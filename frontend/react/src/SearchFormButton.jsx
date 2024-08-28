import React, { useState } from 'react';
import {
    Button,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    FormControl,
    FormLabel,
    Input,
    Stack,
    useDisclosure,
    Divider
} from '@chakra-ui/react';
import { SearchIcon } from '@chakra-ui/icons';

const SearchFormButton = () => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const [step, setStep] = useState('select'); // 'select' or 'input'
    const [searchParameter, setSearchParameter] = useState('');
    const [formData, setFormData] = useState({
        name: '',
        age: '',
        email: '',
    });

    const handleParameterClick = (parameter) => {
        setSearchParameter(parameter);
        setStep('input');
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = () => {
        console.log({ [searchParameter]: formData[searchParameter] });
        // Handle form submission logic here
        resetForm();
        onClose(); // Close the modal after submitting
    };

    const resetForm = () => {
        setSearchParameter('');
        setFormData({
            name: '',
            age: '',
            email: '',
        });
        setStep('select');
    };

    return (
        <>
            <Button
                rightIcon={<SearchIcon />}
                colorScheme="blue"
                onClick={() => {
                    resetForm(); // Ensure form is reset when opening
                    onOpen();
                }}
                top="3"
                left="67%"
            >
                Search
            </Button>

            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent
                    bg="gray.300"
                    color="black"
                    borderRadius="md"
                    boxShadow="md"
                    sx={{
                        '& .chakra-form-control': {
                            bg: 'gray.300',
                        },
                        '& .chakra-input': {
                            bg: 'white',
                            borderColor: 'gray.300',
                            _placeholder: { color: 'gray.500' },
                            _focus: { borderColor: 'blue.500', boxShadow: '0 0 0 1px blue.500' },
                        },
                    }}
                >
                    {step === 'select' && (
                        <ModalHeader marginBottom={3}>Select a search parameter</ModalHeader>
                    )}
                    <ModalCloseButton />
                    <ModalBody>
                        {step === 'select' ? (
                            <Stack spacing={5} maxW={350} marginX={6}>
                                <Button
                                    colorScheme="blue"
                                    onClick={() => handleParameterClick('name')}
                                >
                                    Search by Name
                                </Button>
                                <Button
                                    colorScheme="blue"
                                    onClick={() => handleParameterClick('age')}
                                >
                                    Search by Age
                                </Button>
                                <Button
                                    colorScheme="blue"
                                    onClick={() => handleParameterClick('email')}
                                >
                                    Search by Email
                                </Button>
                            </Stack>
                        ) : (
                            <Stack spacing={4} marginTop={6}>
                                <FormControl id={searchParameter}>
                                    <FormLabel>
                                        {searchParameter.charAt(0).toUpperCase() + searchParameter.slice(1)}:
                                    </FormLabel>
                                    <Input

                                        name={searchParameter}
                                        value={formData[searchParameter]}
                                        onChange={handleChange}
                                        placeholder={`Enter ${searchParameter}`}
                                    />
                                </FormControl>
                            </Stack>
                        )}
                    </ModalBody>

                    <ModalFooter>
                        {step === 'select' ? (
                            <Button

                                mr={3}
                                isDisabled
                                size="1"
                            >

                            </Button>
                        ) : (
                            <>
                                <Button
                                    colorScheme="blue"
                                    mr={3}
                                    onClick={handleSubmit}
                                >
                                    Search
                                </Button>
                                <Button
                                    variant="ghost"
                                    onClick={() => {
                                        resetForm();
                                        onOpen(); // Reopen modal with reset form
                                    }}
                                >
                                    Back to Selection
                                </Button>
                                <Button
                                    variant="ghost"
                                    onClick={onClose}
                                >
                                    Cancel
                                </Button>
                            </>
                        )}
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default SearchFormButton;
